package no.fint.tdd.model

import spock.lang.Specification

class CountySpec extends Specification {

    def "Create county object with attributes identifier and name"() {
        given:
        def county = new County('821311632', 'Vestland fylkeskommune')

        expect:
        county.id == '821311632'
        county.name == 'Vestland fylkeskommune'
    }
}