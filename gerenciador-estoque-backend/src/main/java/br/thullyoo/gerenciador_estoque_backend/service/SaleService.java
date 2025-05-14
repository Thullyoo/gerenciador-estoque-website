package br.thullyoo.gerenciador_estoque_backend.service;

import br.thullyoo.gerenciador_estoque_backend.dto.request.SaleItemRequest;
import br.thullyoo.gerenciador_estoque_backend.dto.response.SaleItemResponse;
import br.thullyoo.gerenciador_estoque_backend.dto.response.SaleResponse;
import br.thullyoo.gerenciador_estoque_backend.entity.Product;
import br.thullyoo.gerenciador_estoque_backend.entity.Sale;
import br.thullyoo.gerenciador_estoque_backend.entity.SaleItem;
import br.thullyoo.gerenciador_estoque_backend.entity.User;
import br.thullyoo.gerenciador_estoque_backend.mapper.SaleMapper;
import br.thullyoo.gerenciador_estoque_backend.repository.ProductRepository;
import br.thullyoo.gerenciador_estoque_backend.repository.SaleItemRepository;
import br.thullyoo.gerenciador_estoque_backend.repository.SaleRepository;
import br.thullyoo.gerenciador_estoque_backend.security.jwt.JwtUtils;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class SaleService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SaleItemRepository saleItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Transactional
    public void registerSale(List<SaleItemRequest> saleItemRequestList) {
        User user = jwtUtils.getUserByToken();

        double total = 0.0;

        Sale sale = new Sale();
        sale.setDate(LocalDateTime.now());
        sale.setSeller(user);
        sale.setTotalAmount(0.0);
        sale = saleRepository.save(sale);

        List<SaleItem> saleItemList = new ArrayList<>();

        for (SaleItemRequest saleItemRequest : saleItemRequestList) {
            Product product = productRepository.findById(saleItemRequest.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getQuantity() < saleItemRequest.quantity()) {
                throw new RuntimeException("Product quantity unavailable");
            }

            product.setQuantity(product.getQuantity() - saleItemRequest.quantity());

            SaleItem saleItem = SaleMapper.toSaleItem(
                    saleItemRequest.quantity(),
                    product.getPrice(),
                    product,
                    sale
            );

            total += product.getPrice() * saleItemRequest.quantity();

            saleItemList.add(saleItem);
            saleItemRepository.save(saleItem);
            productRepository.save(product);
        }

        sale.setItems(saleItemList);
        sale.setTotalAmount(total);
        saleRepository.save(sale);
    }

    public List<SaleResponse> listSalesByUser() {
        User user = jwtUtils.getUserByToken();

        List<Sale> sales = saleRepository.findBySeller(user);

        if (sales.size() <= 0){
            throw new RuntimeException("User doesn't have a sale");
        }

        return sales.stream().map(SaleMapper::toSaleResponse).toList();
    }

    public byte[] generateReportOfSales() {
        return generateReportOfSalesByDate(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX));
    }

    public byte[] generateReportOfSalesByDate(LocalDateTime start, LocalDateTime end) {
        try {
            User user = jwtUtils.getUserByToken();

            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);

            List<SaleResponse> sales = saleRepository.findBySellerAndDateBetween(user, start, end)
                    .stream().map(SaleMapper::toSaleResponse).toList();

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font itemFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedStart = start.toLocalDate().format(dateFormatter);
            String formattedEnd = end.toLocalDate().format(dateFormatter);

            String title;
            if (start.toLocalDate().equals(end.toLocalDate())) {
                title = "Relatório de Vendas - " + formattedStart;
            } else {
                title = "Relatório de Vendas - De " + formattedStart + " até " + formattedEnd;
            }

            document.add(new Paragraph(title, titleFont));
            document.add(Chunk.NEWLINE);

            if (sales.isEmpty()) {
                document.add(new Paragraph("Não há vendas neste período.", contentFont));
            } else {
                for (SaleResponse sale : sales) {
                    document.add(new Paragraph("Data da Venda: " + sale.date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), sectionFont));
                    document.add(new Paragraph("Total: R$ " + String.format("%.2f", sale.total()), contentFont));
                    document.add(new Paragraph("Itens:", contentFont));
                    document.add(Chunk.NEWLINE);

                    for (SaleItemResponse item : sale.saleItemResponses()) {
                        Paragraph itemParagraph = new Paragraph(String.format(
                                "  - %s\n    Código: %d\n    Preço Unitário: R$ %.2f\n    Quantidade: %d",
                                item.name(), item.code(), item.unitPrice(), item.quantity()
                        ), itemFont);
                        document.add(itemParagraph);
                        document.add(Chunk.NEWLINE);
                    }

                    document.add(new Paragraph("------------------------------------------------------------"));
                    document.add(Chunk.NEWLINE);
                }
            }

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage());
        }
    }
}
