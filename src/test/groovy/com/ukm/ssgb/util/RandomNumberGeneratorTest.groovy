package com.ukm.ssgb.util

import spock.lang.Specification

class RandomNumberGeneratorTest extends Specification {

    def "인증번호 6자리 생성"() {
        expect:
        RandomNumberGenerator.generateCertNoString().size() == 6
    }
}
