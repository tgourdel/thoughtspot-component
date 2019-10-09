package com.thoughtspot.talend.components.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.record.Schema;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;
import org.talend.sdk.component.api.service.schema.DiscoverSchema;

import com.thoughtspot.talend.components.dataset.ThoughtSpotDataset;



@Service
public class ThoughtspotComponentService {

    // you can put logic here you can reuse in components
	@DiscoverSchema(family = "ThoughtSpot", value="ThoughtSpotDataset")
    public Schema guessTableSchema(@Option final ThoughtSpotDataset dataset, final RecordBuilderFactory factory) {
		final Schema.Entry.Builder entryBuilder = factory.newEntryBuilder();
		final org.talend.sdk.component.api.record.Schema.Builder schemaBuilder = factory.newSchemaBuilder(Schema.Type.RECORD);
		
		Collection<Schema.Entry> entries = new ArrayList<Schema.Entry>();
		try {
			LinkedHashMap<String, String> rs = dataset.getTableColumns();
			for(String key : rs.keySet())
			{
				if (rs.get(key).equalsIgnoreCase("varchar"))
					schemaBuilder.withEntry(entryBuilder.withName(key).withType(Schema.Type.STRING).build());
				else if (rs.get(key).contains("int"))
					schemaBuilder.withEntry(entryBuilder.withName(key).withType(Schema.Type.INT).build());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return schemaBuilder.build();
    }
}