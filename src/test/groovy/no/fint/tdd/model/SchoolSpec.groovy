package no.fint.tdd.model

import no.fint.model.felles.kompleksedatatyper.Identifikator
import no.fint.model.resource.utdanning.utdanningsprogram.SkoleResource
import spock.lang.Specification

class SchoolSpec extends Specification {

    def "Create school object with attributes identifier and name"() {
        given:
        def school = new School('993527084', 'Nordahl Grieg videregående skole')

        expect:
        school.id == '993527084'
        school.name == 'Nordahl Grieg videregående skole'
    }

    def "Translate skoleresource object to school object"() {
        given:
        def resource = new SkoleResource(systemId: new Identifikator(identifikatorverdi: '993527084'),
                navn: 'Nordahl Grieg videregående skole')

        when:
        def school = School.fromSkoleResource(resource)

        then:
        school.id == '993527084'
        school.name == 'Nordahl Grieg videregående skole'
    }
}