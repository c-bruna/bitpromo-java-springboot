package com.imd.supermercado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.imd.supermercado.model.EmpresaEntity;
import com.imd.supermercado.model.UserEntity;
import com.imd.supermercado.repositories.EmpresaRepository;
import com.imd.supermercado.repositories.UserRepository;
import com.imd.supermercado.security.RoleEnum;
import com.imd.supermercado.DTO.EmpresaDTO;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

   public EmpresaEntity salvarEmpresa(EmpresaDTO dto) {

        EmpresaEntity empresa = new EmpresaEntity();
        empresa.setNome(dto.getNome());
        empresa.setCnpj(dto.getCnpj());
        empresa.setEmail(dto.getEmail());

        EmpresaEntity salva = empresaRepository.save(empresa);

        UserEntity user = new UserEntity();
        user.setLogin(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getSenha()));
        user.setRole(RoleEnum.EMPRESA);
        user.setEmpresa(salva);

        userRepository.save(user);

        return salva;
    }

    public List<EmpresaEntity> listarEmpresas() {
        return empresaRepository.findAll();
    }

    public EmpresaEntity buscarPorId(Long id) {
        Optional<EmpresaEntity> empresa = empresaRepository.findById(id);
        return empresa.orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
    }
      public EmpresaEntity atualizarEmpresa(Long id, EmpresaDTO dto) {
        EmpresaEntity empresa = buscarPorId(id);

        if (dto.getNome() != null) empresa.setNome(dto.getNome());
        if (dto.getCnpj() != null) empresa.setCnpj(dto.getCnpj());
        if (dto.getEmail() != null) empresa.setEmail(dto.getEmail());

        return empresaRepository.save(empresa);
    }

    public boolean deletarEmpresa(Long id) {
        Optional<EmpresaEntity> empresa = empresaRepository.findById(id);
        if (empresa.isPresent()) {
            empresaRepository.delete(empresa.get());
            return true;
        }
        return false;
    }
}