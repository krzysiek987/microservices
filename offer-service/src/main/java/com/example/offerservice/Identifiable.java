package com.example.offerservice;

import java.io.Serializable;

/**
 * Base interface for entities which get persisted as documents in a NoSQL database.
 *
 * @param <I> generic type of id class
 */
public interface Identifiable<I extends Serializable> {

	I getId();

	void setId(I id);

}