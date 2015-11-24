//
// Created by caojianhua on 15/11/24.
//

#include "FrameProcessor.h"

FrameProcessor::FrameProcessor() { }

FrameProcessor::~FrameProcessor() { }

void FrameProcessor::setTempoChange(float temp) { }

void FrameProcessor::shiftingPitch(signed char buffer[], int offset, int length) { }

void FrameProcessor::receiveSamples(signed char buffer[], int length) { }

void FrameProcessor::flush() { }