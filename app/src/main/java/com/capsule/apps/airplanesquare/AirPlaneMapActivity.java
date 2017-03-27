package com.capsule.apps.airplanesquare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.capsule.apps.airplanesquare.widgets.SeatCellView;
import com.capsule.apps.airplanesquare.widgets.SeatMapView;
import com.capsule.apps.airplanesquare.widgets.SeatRowView;

public class AirPlaneMapActivity extends AppCompatActivity {

    private static final String TAG = AirPlaneMapActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_plane_map);

        final SeatMapView seatMapView = (SeatMapView) findViewById(R.id.seat_map_view);
        for (int index = 0; index < 40; index++) {
            seatMapView.addSeatRowView(new SeatRowView(this, null));
        }

    }
}
