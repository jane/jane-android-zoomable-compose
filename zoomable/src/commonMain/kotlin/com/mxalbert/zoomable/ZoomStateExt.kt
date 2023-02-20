package com.mxalbert.zoomable

import androidx.compose.animation.SplineBasedFloatDecayAnimationSpec
import androidx.compose.animation.core.generateDecayAnimationSpec
import androidx.compose.ui.unit.Density

val SampleZoomState = ZoomableState(
    decayAnimationSpec = SplineBasedFloatDecayAnimationSpec(Density(1f)).generateDecayAnimationSpec()
)

val ZoomableState.isZoomingOrGesture: Boolean
    get() = isZooming || isGestureInProgress