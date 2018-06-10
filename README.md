# Mnemonic Major System Utilities

This project is a quick-and-dirty proof of concept of using Lucene to index a
dictionary so as to find good mnemonics for numbers.

The Mnemonic Major System (MMS) with slight modifications for Spanish users assigns a numeric
meaning to words by translating consonants into digits.

| number | consonants       |
|--------|------------------|
| 0      | r, rr            |
| 1      | t, d, ch         |
| 2      | n, ñ             |
| 3      | m                |
| 4      | k, q, ca, co, cu |
| 5      | l                |
| 6      | s, x             |
| 7      | f, z, ce, ci     |
| 8      | g, j             |
| 9      | p, b, v          |

For example, for the number 1004 there are many options being the simplest one `aterroricé` (very
germane one) and `atea ahora ahorca` is a tree word example.

```
Mnemonics for 1004
 * 1: {aterroricé}
 * 2: {ata, ate, atea, ateo, até, ato, ató, auto, ayuda, ayude} {horroricé}
 * 3: {adherí, adhería, adhiera, adhiere, adhiero, adhirió, adora, adore, adoré, adoro} {ahorca, ahorco, ahorcó, ahorque, ahorqué, arca, arco, ericé, heroica, heroico}
 * 4: {adherir, adherirá, adheriré, adheriría, adhiriera, adorar, adorará, adoraré, adoraría, atraer} {acá, ahueca, ahueco, ahuecó, ahueque, ahuequé, aquí, cae, caí, caía}
 * 5: {ata, ate, atea, ateo, até, ato, ató, auto, ayuda, ayude} {aérea, aéreo, ahora, ahoró, ahorra, ahorre, ahorré, ahorro, ahorró, aire} {ahorca, ahorco, ahorcó, ahorque, ahorqué, arca, arco, ericé, heroica, heroico}
 * 6: {ata, ate, atea, ateo, até, ato, ató, auto, ayuda, ayude} {ahorrar, ahorrará, ahorraré, ahorraría, airear, aireará, airearé, airearía, aurora, errar} {acá, ahueca, ahueco, ahuecó, ahueque, ahuequé, aquí, cae, caí, caía}
 * 7: {adherí, adhería, adhiera, adhiere, adhiero, adhirió, adora, adore, adoré, adoro} {aérea, aéreo, ahora, ahoró, ahorra, ahorre, ahorré, ahorro, ahorró, aire} {acá, ahueca, ahueco, ahuecó, ahueque, ahuequé, aquí, cae, caí, caía}
 * 8: {ata, ate, atea, ateo, até, ato, ató, auto, ayuda, ayude} {aérea, aéreo, ahora, ahoró, ahorra, ahorre, ahorré, ahorro, ahorró, aire} {aérea, aéreo, ahora, ahoró, ahorra, ahorre, ahorré, ahorro, ahorró, aire} {acá, ahueca, ahueco, ahuecó, ahueque, ahuequé, aquí, cae, caí, caía}
```