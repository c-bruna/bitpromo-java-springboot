package com.imd.supermercado.services;

import com.imd.supermercado.DTO.PedidoDTO;
import com.imd.supermercado.model.ClienteEntity;
import com.imd.supermercado.model.PedidoEntity;
import com.imd.supermercado.model.ProdutoEntity;
import com.imd.supermercado.repositories.ClienteRepository;
import com.imd.supermercado.repositories.PedidoRepository;
import com.imd.supermercado.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public PedidoEntity salvarPedido(PedidoDTO pedidoDTO) {
        Optional<ClienteEntity> cliente = clienteRepository.findById(pedidoDTO.idCliente());
        ClienteEntity clienteId = cliente.get();

        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setCodigo(pedidoDTO.codigo());
        pedidoEntity.setCliente(clienteId);
        return pedidoRepository.save(pedidoEntity);
    }

    public PedidoEntity atualizarPedido(PedidoDTO pedidoDTO, Long id) {
        Optional<PedidoEntity> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isEmpty()) {
            return null;
        }

        PedidoEntity pedidoEntity = pedidoOptional.get();
        ClienteEntity cliente = null;
        if (pedidoDTO.idCliente() != null) {
            Optional<ClienteEntity> clienteOptional = clienteRepository.findById(pedidoDTO.idCliente());
            if (clienteOptional.isPresent()) {
                cliente = clienteOptional.get();
            }
        }
        List<ProdutoEntity> produtos = null;
        if (pedidoDTO.idProdutos() != null && !pedidoDTO.idProdutos().isEmpty()) {
            produtos = produtoRepository.findAllById(pedidoDTO.idProdutos());
        }

        pedidoEntity.atualizarPedido(pedidoDTO, cliente, produtos);
        return pedidoRepository.save(pedidoEntity);
    }


    public boolean apagarPedido(Long id) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(id);
        if (pedido.isEmpty()) {
            return false;
        }
        pedidoRepository.delete(pedido.get());
        return true;
    }

    public PedidoEntity buscarPedido(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public List<PedidoEntity> buscarPedidos() {
        return pedidoRepository.findAll();
    }

    public List<PedidoEntity> buscarPedidosAtivos() {
        return pedidoRepository.findAllByAtivoTrue();
    }

    public PedidoEntity adicionarProdutos(Long pedidoId, List<Long> idProdutos) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(pedidoId);
        if (pedido.isEmpty()) {
            return null;
        }
        List<ProdutoEntity> produtos = produtoRepository.findAllById(idProdutos);
        if (produtos.isEmpty()) {
            return null;
        }
        PedidoEntity pedidoAdd = pedido.get();
        pedidoAdd.getProdutos().addAll(produtos);
        return pedidoRepository.save(pedidoAdd);
    }

    public PedidoEntity removerProdutos(Long pedidoId, List<Long> idProdutos) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(pedidoId);
        if (pedido.isEmpty()) {
            return null;
        }
        PedidoEntity pedidoDel = pedido.get();
        boolean removed = pedidoDel.getProdutos().removeIf(produto -> idProdutos.contains(produto.getId()));
        if (!removed) {
            return null;
        }
        return pedidoRepository.save(pedidoDel);
    }



    public boolean ativarPedido(Long id) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(id);
        if (pedido.isEmpty()) {
            return false;
        }
        PedidoEntity pedidoEntity = pedido.get();
        pedidoEntity.ativar();
        pedidoRepository.save(pedidoEntity);
        return true;
    }

    public boolean desativarPedido(Long id) {
        Optional<PedidoEntity> pedido = pedidoRepository.findById(id);
        if (pedido.isEmpty()) {
            return false;
        }
        PedidoEntity pedidoEntity = pedido.get();
        pedidoEntity.desativar();
        pedidoRepository.save(pedidoEntity);
        return true;
    }
}




