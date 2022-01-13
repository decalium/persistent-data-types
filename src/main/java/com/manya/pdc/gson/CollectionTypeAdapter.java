/*
 * persistent-data-types
 * Copyright © 2022 Lesya Morozova
 *
 * persistent-data-types is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * persistent-data-types is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with persistent-data-types. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU Lesser General Public License.
 */
package com.manya.pdc.gson;

import com.google.common.base.MoreObjects;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collector;

public final class CollectionTypeAdapter<A, Z extends Collection<E>, E> extends TypeAdapter<Z> {

    private final Collector<E, A, Z> collector;
    private final TypeAdapter<E> elementAdapter;

    public CollectionTypeAdapter(Collector<E, A, Z> collector, TypeAdapter<E> elementAdapter) {

        this.collector = collector;
        this.elementAdapter = elementAdapter;
    }
    @Override
    public void write(JsonWriter out, Z value) throws IOException {
        out.beginArray();
        for(E element : value) {
            elementAdapter.write(out, element);
        }
        out.endArray();
    }

    @Override
    public Z read(JsonReader in) throws IOException {
        A container = collector.supplier().get();
        in.beginArray();
        while(in.hasNext()) {
            collector.accumulator().accept(container, elementAdapter.read(in));
        }
        in.endArray();
        return collector.finisher().apply(container);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionTypeAdapter<?, ?, ?> that = (CollectionTypeAdapter<?, ?, ?>) o;
        return collector.equals(that.collector) && elementAdapter.equals(that.elementAdapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collector, elementAdapter);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("collector", collector)
                .add("elementAdapter", elementAdapter)
                .toString();
    }
}
