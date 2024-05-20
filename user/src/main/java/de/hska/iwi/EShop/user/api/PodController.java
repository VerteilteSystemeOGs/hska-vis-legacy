package de.hska.iwi.EShop.user.api;

import lombok.RequiredArgsConstructor;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatusCode;

@RestController
@RequiredArgsConstructor
public class PodController implements DefaultApi {
    public ResponseEntity<GetHostname200ResponseDTO> getHostname() {
        try {
            return ResponseEntity.ok(GetHostname200ResponseDTO.builder().hostname(InetAddress.getLocalHost().getHostName()).build());
        } catch (UnknownHostException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
