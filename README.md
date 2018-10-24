# vigenere breaker
- [x] Cipher
- [x] Decipher
- [x] Vigenere breaker
  - [x] Bazeries
  - [x] Frequency Analysis
  - [x] Friedman
  - [x] Kasiski

# Usage

Compiled jar accept the following arguments:

    - cypher <plaintTextFilePath> <key>             : Encode a text using vigenere algorithm.
    - uncypher <encodedTextFilePath> <key>          : Decode a text using vigenere algorithm.
    - estimate <encodedTextFilePath>                : Estimate key lengths using Babbage and Kasiski method and Friendman test.
    - frequency <encodedTextFilePath> <keyLength>   : Break a key using frequency analysis.
    - bazeries <encodedTextFilePath> <probableWord> : Try to find the key with a word that is supossed to be in the text.
    - break <encodedTextFilePath>                   : Take a cypher and try to find the key using kasiski and friendman, then frequency analysis.
