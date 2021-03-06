/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.metadata;

import com.facebook.presto.spi.TableHandle;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.annotation.Nullable;

import static com.facebook.presto.metadata.MetadataUtil.checkSchemaName;
import static com.facebook.presto.metadata.MetadataUtil.checkTableName;
import static com.google.common.base.Preconditions.checkArgument;

public class NativeTableHandle
        implements TableHandle
{
    private final String schemaName;
    private final String tableName;
    private final long tableId;
    @Nullable
    private final NativeColumnHandle sampleWeightColumnHandle;

    @JsonCreator
    public NativeTableHandle(@JsonProperty("schemaName") String schemaName,
            @JsonProperty("tableName") String tableName,
            @JsonProperty("tableId") long tableId,
            @JsonProperty("sampleWeightColumnHandle") NativeColumnHandle sampleWeightColumnHandle)
    {
        this.schemaName = checkSchemaName(schemaName);
        this.tableName = checkTableName(tableName);

        checkArgument(tableId > 0, "tableId must be greater than zero");
        this.tableId = tableId;
        this.sampleWeightColumnHandle = sampleWeightColumnHandle;
    }

    @JsonProperty
    public String getSchemaName()
    {
        return schemaName;
    }

    @JsonProperty
    public String getTableName()
    {
        return tableName;
    }

    @JsonProperty
    public long getTableId()
    {
        return tableId;
    }

    @JsonProperty
    public NativeColumnHandle getSampleWeightColumnHandle()
    {
        return sampleWeightColumnHandle;
    }

    @Override
    public String toString()
    {
        return "native:" + schemaName + "." + tableName + ":" + tableId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(schemaName, tableName, tableId);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final NativeTableHandle other = (NativeTableHandle) obj;
        return Objects.equal(this.schemaName, other.schemaName) &&
                Objects.equal(this.tableName, other.tableName) &&
                Objects.equal(this.tableId, other.tableId);
    }
}
