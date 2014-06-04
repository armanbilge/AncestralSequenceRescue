# Ancestral Sequence Rescue

A utility that rescues reconstructed ancestral sequences (and other node attributes) from [BEAST](http://beast.bio.ed.ac.uk/) analyses.

Most likely Java will need additional memory, so execute ASR with the following command (or similar):
`java -Xms512 -Xmx2G -jar asr.jar`

Alternatively, specify the tree file and at least two taxa whose MRCA's node attributes will be printed to stdout; i.e.
`java -Xms512 -Xmx2G -jar asr.jar [tree file] [taxon1] [taxon2] ...`

[**DOWNLOAD**](http://github.com/armanbilge/AncestralSequenceRescue/releases)

[![Build Status](https://travis-ci.org/armanbilge/AncestralSequenceRescue.svg?branch=master)](https://travis-ci.org/armanbilge/AncestralSequenceRescue)

![ASR Screenshot](asr.png)
