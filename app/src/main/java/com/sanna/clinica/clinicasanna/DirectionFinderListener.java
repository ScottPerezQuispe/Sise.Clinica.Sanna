package com.sanna.clinica.clinicasanna;

import java.util.List;

/**
 * Created by scott on 20/05/2018.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}

