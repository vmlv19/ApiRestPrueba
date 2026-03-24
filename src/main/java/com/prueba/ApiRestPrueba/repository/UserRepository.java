package com.prueba.ApiRestPrueba.repository;

import com.prueba.ApiRestPrueba.model.Address;
import com.prueba.ApiRestPrueba.model.User;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>(Arrays.asList(
            new User(
                    UUID.randomUUID(),
                    "user1@mail.com",
                    "user1",
                    "+1 55 555 555 55",
                    "lfPZT+oUslX/RUrWmqbuQ5mX340mKGMswoNcD1N22dCHBma7dCbi6KIBIwTrPCfI",
                    "AARR990101XXX",
                    "01-01-2026 00:00:00",
                    Arrays.asList(
                            new Address(1, "workaddress", "street No. 1", "UK"),
                            new Address(2, "homeaddress", "street No. 2", "AU")
                    )
            ),

            new User(
                    UUID.randomUUID(),
                    "user2@mail.com",
                    "user2",
                    "+1 55 555 555 56",
                    "lfPZT+oUslX/RUrWmqbuQ5mX340mKGMswoNcD1N22dCHBma7dCbi6KIBIwTrPCfI",
                    "BBRR990101XXX",
                    "01-01-2026 00:00:00",
                    Arrays.asList(
                            new Address(1, "workaddress", "street No. 3", "US"),
                            new Address(2, "homeaddress", "street No. 4", "MX")
                    )
            ),

            new User(
                    UUID.randomUUID(),
                    "user3@mail.com",
                    "user3",
                    "+1 55 555 555 57",
                    "lfPZT+oUslX/RUrWmqbuQ5mX340mKGMswoNcD1N22dCHBma7dCbi6KIBIwTrPCfI",
                    "CCRR990101XXX",
                    "01-01-2026 00:00:00",
                    Arrays.asList(
                            new Address(1, "workaddress", "street No. 5", "CA"),
                            new Address(2, "homeaddress", "street No. 6", "FR")
                    )
            )
    ));

    public List<User> findAll(){
        return users;
    }

    public void save(User user) {
        users.add(user);
    }

    public void update(User user){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(1).getId().equals(user.getId())){
                users.set(i, user);
            }
        }
    }

    public void delete(UUID id) {
        users.removeIf(u -> u.getId().equals(id));
    }

    public boolean existsByTaxId(String taxId) {
        return users.stream().anyMatch(
                u -> u.getTaxId().equals(taxId)
        );
    }
}
