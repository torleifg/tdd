package no.fint.tdd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.fint.model.resource.utdanning.utdanningsprogram.SkoleResource;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class School {
    private String id;
    private String name;

    public static School fromSkoleResource(SkoleResource resource) {
        return new School(resource.getSystemId().getIdentifikatorverdi(), resource.getNavn());
    }
}
