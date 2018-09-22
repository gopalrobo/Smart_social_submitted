/*
 * Copyright (C) 2014 The Android Open Source Project
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

package smart.social.worker.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class MapListActivityImpl extends MapListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected MapLocationAdapter createMapListAdapter() {
        ArrayList<MapLocation> mapLocations = new ArrayList<>(LIST_LOCATIONS.length);
        mapLocations.addAll(Arrays.asList(LIST_LOCATIONS));

        MapLocationAdapter adapter = new MapLocationAdapter();
        adapter.setMapLocations(mapLocations);

        return adapter;
    }

    @Override
    public void showMapDetails(View view) {
        MapLocation mapLocation = (MapLocation) view.getTag();

        Intent intent = new Intent(this, MainActivityRoute.class);
        intent.putExtra("map",mapLocation.name);
        startActivity(intent);
    }

    private static final MapLocation[] LIST_LOCATIONS = new MapLocation[]{
            new MapLocation("Day1", 11.473448, 77.808423,"Time : 2018 Mon Mar 23 09:00:00 to 06:00:00\n6 plots Visited"),
            new MapLocation("Day2", 11.347449, 78.983462,"Time : 2018 Tue Jul 23 09:00:00 to 06:00:00\n6 plots Visited"),
            new MapLocation("Day3", 11.360620, 78.972936,"Time : 2018 Wen Jul 23 09:00:00 to 06:00:00\n6 plots Visited"),
    };
}
