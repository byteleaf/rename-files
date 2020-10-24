import spock.lang.IgnoreRest
import spock.lang.Specification
import spock.lang.Unroll

class DataDrivenExampleSpec extends Specification {
    def "maximum of two numbers"() {
        expect:
        // exercise math method for a few different inputs
        Math.max(1, 3) == 3
        Math.max(7, 4) == 7
        Math.max(0, 0) == 0
    }


    @Unroll("The max between #a and #b should be #c")
    @IgnoreRest
    def "DataPipe - maximum of two numbers"() {
        expect:
        Math.max(a, b) == c
        where:
        a << [6, 7, 0]
        b << [5, 0, 0]
        c << [5, 7, 0]
    }
}