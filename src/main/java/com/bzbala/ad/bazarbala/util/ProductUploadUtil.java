package com.bzbala.ad.bazarbala.util;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class ProductUploadUtil {

	public Supplier<Stream<Row>> getRowStreamSupplier(Iterable<Row> rows) {
		return () -> getStream(rows);
	}

	public <T> Stream<T> getStream(Iterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false);
	}

	public Supplier<Stream<Integer>> getCellStreamSupplier(int lineEnd) {
		return () -> getNumberStream(lineEnd);
	}

	private Stream<Integer> getNumberStream(int lineEnd) {

		return IntStream.range(0, lineEnd).boxed();
	}

}
