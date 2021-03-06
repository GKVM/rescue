package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.dropwizard.jackson.JsonSnakeCase;
import json.ObjectIdDeserializer;
import json.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

import java.util.Set;

@JsonSnakeCase
@Entity(value = "user", noClassnameStored = true)
public class Rescue {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    private ObjectId id;
    private String phone;
    private String name;
    @Indexed(IndexDirection.GEO2D)
    private double[] location = new double[2];
    private String request;
    private Set<String> items;
    private Long updated;

    public Rescue() {
    }

    @JsonCreator
    public Rescue(
            @JsonProperty("id") @JsonSerialize(using = ObjectIdSerializer.class) ObjectId id,
            @JsonProperty("phone") String phone,
            @JsonProperty("name") String name,
            @JsonProperty("location") final double[] location,
            @JsonProperty("request") String request,
            @JsonProperty("updated") Long updated
    ) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.request = request;
        this.updated = updated;
        this.location = location;
    }

    public ObjectId getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public double[] getLocation() {
        return location;
    }

    public Set<String> getItems() {
        return items;
    }

    public Long getUpdated() {
        return updated;
    }

    public String getRequest() {
        return request;
    }
}
