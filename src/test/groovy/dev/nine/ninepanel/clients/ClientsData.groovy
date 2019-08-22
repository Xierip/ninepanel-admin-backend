package dev.nine.ninepanel.clients

import com.mongodb.BasicDBObject
import dev.nine.ninepanel.clients.addressdetails.AddressDetails
import dev.nine.ninepanel.clients.domain.ClientRoles
import dev.nine.ninepanel.clients.domain.dto.ClientDto
import dev.nine.ninepanel.infrastructure.constant.MongoCollections
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate

trait ClientsData {

  @Autowired
  MongoTemplate mongoTemplate

  ClientDto setUpClient(String email) {
    Map<String, Object> client = new HashMap<>()

    ObjectId id = new ObjectId()
    AddressDetails addressDetails = AddressDetails.builder()
        .address("test address")
        .city("Bialystok")
        .country("Russia")
        .zipCode("1337")
        .build()

    ClientDto dto = ClientDto.builder()
        .id(id)
        .email(email)
        .password("test_pass_123")
        .name("test name")
        .surname("test surname")
        .phoneNumber("123 456 789")
        .addressDetails(addressDetails)
        .companyDetails(null)
        .displayName("test name test surname")
        .role(ClientRoles.DEMO)
        .build()

    client.put("_id", id)
    client.put("email", email)
    client.put("password", dto.password)
    client.put("name", dto.name)
    client.put("surname", dto.surname)
    client.put("phoneNumber", dto.phoneNumber)
    client.put("address", dto.addressDetails)
    client.put("companyDetails", dto.companyDetails)
    client.put("displayName", dto.name + " " + dto.surname)
    client.put("role", ClientRoles.DEMO)

    mongoTemplate.insert(new BasicDBObject(client), MongoCollections.CLIENTS)
    return dto
  }


}