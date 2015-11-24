//
// Created by caojianhua on 15/11/24.
//

#ifndef SOUNDTOUCHEXAMPLE_FRAMEPROCESSOR_H
#define SOUNDTOUCHEXAMPLE_FRAMEPROCESSOR_H


class FrameProcessor {
public:
    FrameProcessor();
    virtual ~FrameProcessor();
    void setTempoChange(float temp);
    void shiftingPitch(signed char buffer[], int offset, int length);
    void receiveSamples(signed char buffer[], int length);

    void flush();
};


#endif //SOUNDTOUCHEXAMPLE_FRAMEPROCESSOR_H
