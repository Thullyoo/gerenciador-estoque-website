package br.thullyoo.gerenciador_estoque_backend.controller;

import br.thullyoo.gerenciador_estoque_backend.dto.request.SaleItemRequest;
import br.thullyoo.gerenciador_estoque_backend.dto.response.SaleResponse;
import br.thullyoo.gerenciador_estoque_backend.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerSale(@RequestBody ArrayList<SaleItemRequest> listSaleItemRequest){
        saleService.registerSale(listSaleItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/mysales")
    public ResponseEntity<List<SaleResponse>> listSalesByUser(){
        return ResponseEntity.status(HttpStatus.OK).body(saleService.listSalesByUser());
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> generateReportOfSales() {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(saleService.generateReportOfSales());
    }

    @GetMapping("/report/{start}/{end}")
    public ResponseEntity<byte[]> generateReportOfSales(@PathVariable("start")LocalDateTime start, @PathVariable("end")LocalDateTime end) {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(saleService.generateReportOfSalesByDate(start, end));
    }

}
