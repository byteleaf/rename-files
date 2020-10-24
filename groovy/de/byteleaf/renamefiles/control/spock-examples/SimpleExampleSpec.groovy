import spock.lang.Specification

class SimpleExampleSpec extends Specification {

    def "Create a new user with name 'Hans' and add him to the user list"() {
        given: "prepare the user list list"
        def userList = new ArrayList()
        userList.add("Jeff")

        and: "create a new user with name 'Hans' and add him to the user list"
        userList.add("Hans")

        expect: "The new user should be contained in the user list now"
        userList.contains("Hans")
    }
}