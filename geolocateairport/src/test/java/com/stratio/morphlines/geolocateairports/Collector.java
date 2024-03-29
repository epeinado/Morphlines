/**
 * Copyright (C) 2015 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
* Copyright 2013 Cloudera Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.stratio.morphlines.geolocateairports;

import com.google.common.base.Preconditions;
import org.kitesdk.morphline.api.Command;
import org.kitesdk.morphline.api.Record;
import org.kitesdk.morphline.base.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class Collector implements Command {

    private Command parent;
    private List<Record> records;
    private int numStartEvents;

    private static final Logger LOG = LoggerFactory.getLogger(Collector.class);

    public Collector() {
        reset();
    }

    public void reset() {
        records = new ArrayList<Record>();
        numStartEvents = 0;
    }

    public Command getParent() {
        return parent;
    }

    public void notify(Record notification) {
        if (Notifications.containsLifecycleEvent(notification, Notifications.LifecycleEvent.START_SESSION)) {
            numStartEvents++;
        }
    }

    public boolean process(Record record) {
        Preconditions.checkNotNull(record);
        records.add(record);
        return true;
    }

    public List<Record> getRecords() {
        return records;
    }

    public Record getFirstRecord() {
        if (records.size() != 1) {
            throw new IllegalStateException();
        }
        if (records.get(0) == null) {
            throw new IllegalStateException();
        }
        return records.get(0);
    }

    public int getNumStartEvents() {
        return numStartEvents;
    }

}