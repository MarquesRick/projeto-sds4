package com.devsuperior.dsvendas.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsvendas.dto.SaleDTO;
import com.devsuperior.dsvendas.dto.SaleSuccessDTO;
import com.devsuperior.dsvendas.dto.SaleSumDTO;
import com.devsuperior.dsvendas.entities.Sale;
import com.devsuperior.dsvendas.repositories.SaleRepository;
import com.devsuperior.dsvendas.repositories.SellerRepository;

@Service
public class SaleService {
	
	//Instancia injetada automaticamente pelo framework
	@Autowired
	private SaleRepository repository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	//essa annotation garante que toda operação com o banco seja resolvido nessa function
	//readOnly para evitar Lock no banco
	@Transactional(readOnly = true)
	public Page<SaleDTO> findAll(Pageable pageable){
		//forma para buscar os vendedores, JPA salvar em memória para não ficar consultando uma a uma 
		//recomendável utilizar apenas em tabelas com poucos registros < 1000 registros
		sellerRepository.findAll();
		Page<Sale> result = repository.findAll(pageable);
		return result.map(x -> new SaleDTO(x));
	}
	
	@Transactional(readOnly = true)
	public List<SaleSumDTO> amountGroupedBySeller(){
		return repository.amountGroupedBySeller();
	}
	
	@Transactional(readOnly = true)
	public List<SaleSuccessDTO> saleSuccessGroupedBySeller(){
		return repository.successGroupedBySeller();
	}

}
