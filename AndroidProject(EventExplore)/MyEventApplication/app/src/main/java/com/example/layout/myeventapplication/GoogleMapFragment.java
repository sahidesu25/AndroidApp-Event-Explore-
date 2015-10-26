package com.example.layout.myeventapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class GoogleMapFragment extends Fragment {

    private static final int GPS_ERROR = 9001;
    private static final double BANG_LONG = 77.57, BANG_LAT = 12.58;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    public static final float DEFAULT_ZOOM = 15;
    private SupportMapFragment mapFrag;
    private MapView mapView;
    private GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;

    double longitude1, latitude1;

    private View view;
    private static String Address_;
    public static GoogleMapFragment newInstance(String Address) {

        GoogleMapFragment f = new GoogleMapFragment();
         Address_=Address;
        return f;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.google_fragment, container, false);

        if (servicesOK()) {

            if (initMap()) {
                Toast.makeText
                        (getActivity(), "Map Ready", Toast.LENGTH_SHORT).show();

                LocationManager locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                boolean network_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);//both made gps_provider

                Location location;

                if (network_enabled) {

                    location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {
                        longitude1 = location.getLongitude();
                        latitude1 = location.getLatitude();
                        LatLng current = new LatLng(latitude1, latitude1);
                        gotoLocation(latitude1, longitude1, DEFAULT_ZOOM);
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude1, longitude1))
                                .title("CurrentLocation").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {

                                String lo = Address_;
                                Geocoder gc = new Geocoder(getActivity());
                                List<Address> list = null;
                                try {
                                    list = gc.getFromLocationName(lo, 1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Address add = list.get(0);
                                double lat = add.getLatitude();
                                double lon = add.getLongitude();
                                Thread t = new Thread(new MyDir(latitude1, longitude1, lat, lon));
                                t.start();


                            }
                        });

                    }
                }


            } else
                Toast.makeText(getActivity(), "Map not Ready", Toast.LENGTH_SHORT).show();
        } else
            view = inflater.inflate(R.layout.activity_main, container, false);


        return view;
    }

    public boolean initMap() {
        if (googleMap == null) {

            // MapFragment map = ((MapFragment) getFragmentManager().findFragmentById(R.id.maps1));
            // mapView = (MapView)view.findViewById(R.id.maps1);
            // mapFrag = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.maps1);
            // mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentByTag(MAP_FRAGMENT_TAG);

            mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps1);
            googleMap = mapFrag.getMap();
            googleMap.setMyLocationEnabled(true);
        }


        return (googleMap != null);
    }

    public void gotoLocation(double lat, double lon, float zoom) {
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        googleMap.moveCamera(update);

    }

    public boolean servicesOK() {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (isAvailable == ConnectionResult.SUCCESS) return true;
        else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, getActivity(), GPS_ERROR);
            dialog.show();
        } else
            Toast.makeText(getActivity(), "Cant connect to GooglePlay Services", Toast.LENGTH_SHORT).show();
        return false;

    }

    public class MyDir implements Runnable {

        double u, v, w, x;

        public MyDir(double u, double v, double w, double x) {
            this.u = u;
            this.v = v;
            this.w = w;
            this.x = x;
        }

        public void run() {

            Location loc1 = new Location("");
            loc1.setLatitude(u);
            loc1.setLongitude(v);

            Location loc2 = new Location("");
            loc2.setLatitude(w);
            loc2.setLongitude(x);

            float distanceInMeters = loc1.distanceTo(loc2);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + u + "," + v + "&daddr=" + w + "," + x));
            startActivity(intent);

        }

    }
}
