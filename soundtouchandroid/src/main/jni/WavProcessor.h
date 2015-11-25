//
// Created by caojianhua on 15/11/24.
//

#ifndef SOUNDTOUCHEXAMPLE_WAVPROCESSOR_H
#define SOUNDTOUCHEXAMPLE_WAVPROCESSOR_H

#include <string>

class WavProcessor {
public:
    void processWav(std::string src, float tempoDelta, float pitchDelta, float rateDelta, std::string dst);
};


#endif //SOUNDTOUCHEXAMPLE_WAVPROCESSOR_H
