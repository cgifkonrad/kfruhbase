/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cloud.bigtable.kfruinit;

// [START bigtable_hw_imports]
import com.google.cloud.bigtable.hbase.BigtableConfiguration;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
// [END bigtable_hw_imports]

/**
 * A minimal application that connects to Cloud Bigtable using the native HBase API and performs
 * some basic operations.
 */
public class KfruInit {

  /** Connects to Cloud Bigtable, runs some basic operations and prints the results. */
  private static void createTable(String projectId, String instanceId , byte[] tableName, byte[] colFamilyOne, byte[] colFamilyTwo ) {

    // [START bigtable_hw_connect]
    // Create the Bigtable connection, use try-with-resources to make sure it gets closed
    try (Connection connection = BigtableConfiguration.connect(projectId, instanceId)) {

      // The admin API lets us create, manage and delete tables
      Admin admin = connection.getAdmin();
      // [END bigtable_hw_connect]

      try {
        // [START bigtable_hw_create_table]
        // Create a table with a single column family
        HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
        descriptor.addFamily(new HColumnDescriptor(colFamilyOne));
        if ( colFamilyTwo != null )
            descriptor.addFamily(new HColumnDescriptor(colFamilyTwo));
        admin.createTable(descriptor);
        // [END bigtable_hw_create_table]

        // Retrieve the table we just created so we can do some reads and writes
        // Informationen zur Table-Klasse:
        //
        //      https://hbase.apache.org/apidocs/org/apache/hadoop/hbase/client/Table.html
        //
        Table table = connection.getTable(TableName.valueOf(tableName));
        System.out.println(table);
      } 
      catch (IOException e) {
        if (admin.tableExists(TableName.valueOf(tableName))) {
          System.out.println("Cleaning up table");
          admin.disableTable(TableName.valueOf(tableName));
          admin.deleteTable(TableName.valueOf(tableName));
        }
        throw e;
          }
         } catch (IOException e) {
      System.err.println("Exception at Table Creation" + e.getMessage());
      System.exit(1);
    }
} /*catch (IOException e) {
      System.err.println("Exception while running KfruInit: " + e.getMessage());
      e.printStackTrace();

      System.exit(1);
    }
}*/
   
      public static void putSingleCell (Table table, byte[] rowKey, byte[] columnFamily, byte[] column , byte[] value){

          // For more information about how to design a Bigtable schema for the
          // best performance, see the documentation:
          //
          //     https://cloud.google.com/bigtable/docs/schema-design
          // 
          // Funktionalitaeten der Put-Klasse:
          //
          //     https://hbase.apache.org/apidocs/org/apache/hadoop/hbase/client/Put.html
          //
          // Put a single row into the table. We could also pass a list of Puts to write a batch.
          Put put = new Put(rowKey);
          put.addColumn(columnFamily, column , value);
          try {
             table.put(put);
          } 
          catch (IOException e ) {
            System.err.println("Could not write Row" + e.getMessage() );
          }
        }


      
    

  
        
        public static void scanTable (Table table ) {
        // [START bigtable_hw_scan_all]
        // Now scan across all rows.
        Scan scan = new Scan();

        System.out.println("Scanning Table:");
                  try {
                      ResultScanner scanner = table.getScanner(scan);
                      System.out.println( scanner );
          } 
          catch (IOException e ) {
            System.err.println("Could not scan Table" + e.getMessage() );
          }
        // [END bigtable_hw_scan_all]
        }

        public static void deleteTable (String projectId, String instanceId, byte[] tableName){
            try (Connection connection = BigtableConfiguration.connect(projectId, instanceId)) {
                Admin admin = connection.getAdmin();
                // [START bigtable_hw_delete_table]
                // Clean up by disabling and then deleting the table
                if (admin.tableExists(TableName.valueOf(tableName))) {
          System.out.println("Cleaning up table");
          admin.disableTable(TableName.valueOf(tableName));
          admin.deleteTable(TableName.valueOf(tableName));
                // [END bigtable_hw_delete_table]
            }
        }
            
            catch(IOException e ){
                System.err.println("Exception at Table Creation" + e.getMessage());
                System.exit(1);
        }
            }


  public static void main(String[] args) {
    // Consult system properties to get project/instance
    String projectId = requiredProperty("bigtable.projectID");
    String instanceId = requiredProperty("bigtable.instanceID");

    
  }

  private static String requiredProperty(String prop) {
    String value = System.getProperty(prop);
    if (value == null) {
      throw new IllegalArgumentException("Missing required system property: " + prop);
    }
    return value;
  }
}
