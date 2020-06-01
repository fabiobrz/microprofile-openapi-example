package com.example.microprofile.openapi;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
//import java.util.SortedSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Encoded;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/fruit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {

    private final Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public FruitResource() {
	this.fruits.add(new Fruit("Apple", "Winter fruit"));
	this.fruits.add(new Fruit("Pineapple", "Tropical fruit"));
    }

    @GET
    public Set<Fruit> all() {
	return this.fruits;
    }

    @POST
    public Set<Fruit> add(Fruit fruit) {
	this.fruits.add(fruit);
	return this.fruits;
    }

    @DELETE
    public Set<Fruit> remove(Fruit fruit) {
	this.fruits.removeIf(existingFruit -> existingFruit.getName().contentEquals(fruit.getName()));
	return this.fruits;
    }

    @POST
    @Path("form")
    @Consumes("application/x-www-form-urlencoded")
    public String post(@Encoded @FormParam("param") String param) {
	return param;
    }

    //	form param examples
    @Path(value = "/FormAsListOfString")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response formAsListOfString(
	    @Encoded @DefaultValue("ListConstructor") @FormParam("default_argument") List<String> defaultArgument) {
	return Response.ok(defaultArgument.listIterator().next())
		.build();
    }

    @Path(value = "/FormAsSetOfString")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response formAsSetOfString(
	    @Encoded @DefaultValue("SortedSetFromString") @FormParam("default_argument") Set<String> defaultArgument) {
	return Response.ok(defaultArgument.iterator().next())
		.build();
    }

    //	comment this method in order to deploy successfully
    @Path(value = "/FormAsSortedSetOfString")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response formAsSortedSetOfString(
	    @Encoded @DefaultValue("SortedSetFromString") @FormParam("default_argument") SortedSet<String> defaultArgument) {
	return Response.ok(defaultArgument.first())
		.build();
    }
}
