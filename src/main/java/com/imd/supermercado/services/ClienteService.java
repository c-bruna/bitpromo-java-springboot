package com.imd.supermercado.services;

import com.imd.supermercado.DTO.ClienteDTO;
import com.imd.supermercado.model.ClienteEntity;
import com.imd.supermercado.model.UserEntity;
import com.imd.supermercado.repositories.ClienteRepository;
import com.imd.supermercado.repositories.UserRepository;
import com.imd.supermercado.security.RoleEnum;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository repository;

     @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClienteEntity salvarCliente(ClienteDTO clienteDTO) {

        ClienteEntity clienteEntity = new ClienteEntity(clienteDTO);
        BeanUtils.copyProperties(clienteDTO, clienteEntity, "senha"); 
        clienteEntity = repository.save(clienteEntity);

        UserEntity user = new UserEntity();
        user.setLogin(clienteDTO.getEmail());
        user.setPassword(passwordEncoder.encode(clienteDTO.getSenha()));
        user.setRole(RoleEnum.USER);
        user.setCliente(clienteEntity);

        userRepository.save(user);

        return clienteEntity;
    }


    public ClienteEntity atualizarCliente(ClienteDTO clienteDTO, Long id) {
        Optional<ClienteEntity> cliente = repository.findById(id);
        if (cliente.isEmpty()) {
            return null;
        }
        ClienteEntity clienteEntity = cliente.get();
        clienteEntity.atualizarCliente(clienteDTO);
        return repository.save(clienteEntity);
    }

    public boolean ApagarCliente(Long id) {
        Optional<ClienteEntity> cliente = repository.findById(id);
        if(cliente.isEmpty()){
            return false;
        }
        repository.delete(cliente.get());
        return true;
    }

    public ClienteEntity buscarCliente(Long id){
        Optional<ClienteEntity> cliente = repository.findById(id);

        if(cliente.isEmpty()){
            return null;
        }
        else{
            return cliente.get();
        }
    }

    public List<ClienteEntity> buscarClientesAtivos(){
        return repository.findAllByAtivoTrue();
    }

    public List<ClienteEntity> buscarClientes(){
        return repository.findAll();
    }

    public boolean ativarCliente(Long id){
        Optional<ClienteEntity> cliente = repository.findById(id);
        if(cliente.isEmpty()){
            return false;
        }
        ClienteEntity clienteEntity = cliente.get();
        clienteEntity.ativar();
        repository.save(clienteEntity);
        return true;
    }

    public boolean desativarCliente(Long id){
        Optional<ClienteEntity> cliente = repository.findById(id);
        if(cliente.isEmpty()){
            return false;
        }
        ClienteEntity clienteEntity = cliente.get();
        clienteEntity.desativar();
        repository.save(clienteEntity);
        return true;
    }

    public ClienteRepository repository(){
        return repository;
    }
}
