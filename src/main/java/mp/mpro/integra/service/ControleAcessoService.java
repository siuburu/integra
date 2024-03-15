package mp.mpro.integra.service;

import jakarta.transaction.Transactional;
import java.time.Instant;
import mp.mpro.integra.domain.ControleAcesso;
import mp.mpro.integra.repository.ControleAcessoRepository;
import mp.mpro.integra.repository.UserRepository;
import mp.mpro.integra.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ControleAcessoService {

    private final Logger log = LoggerFactory.getLogger(ControleAcessoService.class);

    private final UserRepository userRepository;
    private final ControleAcessoRepository controleAcessoRepository;

    public ControleAcessoService(
        UserRepository userRepository,
        ControleAcessoRepository controleAcessoRepository,
        CacheManager cacheManager
    ) {
        this.userRepository = userRepository;
        this.controleAcessoRepository = controleAcessoRepository;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        log.debug("LOGIN OCORREU COM SUCESSO!");
        //        log.debug("PRINCIPAL "+ success.getAuthentication().getPrincipal());
        //        log.debug("NAME "+ success.getAuthentication().getName());
        //        log.debug("CREDENTIAL "+ success.getAuthentication().getCredentials());
        //        log.debug("DETAILS "+ success.getAuthentication() .getDetails());
        //        log.debug("Authorities "+ success.getAuthentication() .getAuthorities());

        try {
            WebAuthenticationDetails webDetails = WebAuthenticationDetails.class.cast(success.getAuthentication().getDetails());
            //Class<?>webDetails = success.getAuthentication().getDetails().getClass();
            registraAcesso(success.getAuthentication().getName(), webDetails.getRemoteAddress());
        } catch (Exception e) {
            log.debug("Erro: " + e.getMessage());
        }
    }

    private void registraAcesso(String login, String remoteIp) {
        log.debug("Iniciando Registro de Acesso... ");
        userRepository
            .findOneByLogin(login)
            .ifPresent(user -> {
                ControleAcesso controleAcesso = new ControleAcesso();
                controleAcesso.setDataAcesso(Instant.now());
                controleAcesso.setIpAcesso(remoteIp);
                controleAcesso.setUser(user);
                // TODO getDevice() | User-Agent
                controleAcesso.setNomeDispositivo("Dummy");
                log.debug("Salvando acesso do usuário " + user.getLogin());
                controleAcessoRepository.save(controleAcesso);
                log.debug("Acesso Salvo em " + controleAcesso.getDataAcesso().toString());
            });
    }
}
