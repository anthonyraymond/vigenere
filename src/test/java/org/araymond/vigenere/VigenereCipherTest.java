package org.araymond.vigenere;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by raymo on 22/11/2016.
 */
public class VigenereCipherTest {

    private final VigenereCipher vigenere = new VigenereCipher();

    @Test
    public void shouldComputeSimpleKeyAsNumericalValue() {
        assertThat(vigenere.computeKeyAsNumericalValue("abc")).isEqualTo(new int[]{97, 98, 99});
    }

    @Test
    public void shouldComputeComplexKeyAsNumericalValue() {
        assertThat(vigenere.computeKeyAsNumericalValue("q DQk klsqlkd")).isEqualTo(new int[]{113, 32, 68, 81, 107, 32, 107, 108, 115, 113, 108, 107, 100});
    }

    @Test
    public void shouldComputeCypherSubstituteChar() {
        assertThat(vigenere.computeCypherSubstituteChar('a', 'a')).isEqualTo('a');
        assertThat(vigenere.computeCypherSubstituteChar('a', 'z')).isEqualTo('z');
        assertThat(vigenere.computeCypherSubstituteChar('b', 'i')).isEqualTo('j');
    }

    @Test
    public void shouldComputeUncypherSubstituteChar() {
        assertThat(vigenere.computeUncypherSubstituteChar('a', 'a')).isEqualTo('a');
        assertThat(vigenere.computeUncypherSubstituteChar('a', 'z')).isEqualTo('b');
        assertThat(vigenere.computeUncypherSubstituteChar('b', 'i')).isEqualTo('t');
        assertThat(vigenere.computeUncypherSubstituteChar('z', 'i')).isEqualTo('r');
    }

    @Test
    public void shouldCypherSimpleText() {
        final String plaintText = "iamapancake";
        final String key = "lemon";

        assertThat(vigenere.cypher(plaintText, key)).isEqualTo("teyoclrooxp");
    }

    @Test
    public void shouldCypherComplexText() {
        final String plaintText = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        final String key = "lemon";

        assertThat(vigenere.cypher(plaintText, key)).isEqualTo("wsdsztteiztwewzapkrhxqkhrixatgsibfvyxubtlrphlaiesgemzuvyhgggccxcepqudffqtofmiqbgsiubqfwffldwfoaoedrqfqymgpbfsipvewanifvrdatsalrgbxysibccmzhrcxacxlkmzypcatgjtqoaowofnxfxsqtxfczloqogjtqgcpguaryfacxtxtofdydjvgipbbeszzlqmhspprfietiepheexgbelqzrltubgzixspevabvnxkdrdifhvykdszlmzwariegryxuoywcgbpsezuromfkndtadhwedwfphubgsiekvelfvrcixsndiatypxdofpxevrpxeqbyxmwatrszbciywcdyydndwmurdezrzzvqfrnizhyjauhuoieygztbiowmevvykecseamfrwmwsnwhggclkqanvidwanpgrvykhsedmabfzjxcepqudffq");
    }

    @Test
    public void shouldUncypherSimpleText() {
        final String plaintText = "teyoclrooxp";
        final String key = "lemon";

        assertThat(vigenere.uncypher(plaintText, key)).isEqualTo("iamapancake");
    }

    @Test
    public void shouldUncypherComplexText() {
        final String plaintText = "wsdsztteiztwewzapkrhxqkhrixatgsibfvyxubtlrphlaiesgemzuvyhgggccxcepqudffqtofmiqbgsiubqfwffldwfoaoedrqfqymgpbfsipvewanifvrdatsalrgbxysibccmzhrcxacxlkmzypcatgjtqoaowofnxfxsqtxfczloqogjtqgcpguaryfacxtxtofdydjvgipbbeszzlqmhspprfietiepheexgbelqzrltubgzixspevabvnxkdrdifhvykdszlmzwariegryxuoywcgbpsezuromfkndtadhwedwfphubgsiekvelfvrcixsndiatypxdofpxevrpxeqbyxmwatrszbciywcdyydndwmurdezrzzvqfrnizhyjauhuoieygztbiowmevvykecseamfrwmwsnwhggclkqanvidwanpgrvykhsedmabfzjxcepqudffq";
        final String key = "lemon";

        assertThat(vigenere.uncypher(plaintText, key)).isEqualTo("loremipsumissimplydummytextoftheprintingandtypesettingindustryloremipsumhasbeentheindustrysstandarddummytexteversincetheswhenanunknownprintertookagalleyoftypeandscrambledittomakeatypespecimenbookithassurvivednotonlyfivecenturiesbutalsotheleapintoelectronictypesettingremainingessentiallyunchangeditwaspopularisedintheswiththereleaseofletrasetsheetscontainingloremipsumpassagesandmorerecentlywithdesktoppublishingsoftwarelikealduspagemakerincludingversionsofloremipsum");
    }

    @Test
    public void shouldCypherThenUncypherAndGetThePlainTextBack() {
        final String plaintText = "djhdjhaoihdugqdjqsdhqsbdsquytdfgcvbjiuytresdfcgvhbjiuytredfgcvbjkiuytrdgfvnbjmuytrfcbvjhlhfdjsg";
        final String key = "lemon";

        assertThat(vigenere.uncypher(vigenere.cypher(plaintText, key), key)).isEqualTo(plaintText);
    }

    @Test
    public void shouldNotFailWithEmptyTextWhenCyphering() {
        final String plaintText = "";
        final String key = "lemon";

        assertThat(vigenere.cypher(plaintText, key)).isEqualTo(plaintText);
    }

    @Test
    public void shouldNotFailWithEmptyKeyWhenCyphering() {
        final String plaintText = "djhdjhaoihdugqdjqsdhqsbdsquytdfgcvbjiuytresdfcgvhbjiuytredfgcvbjkiuytrdgfvnbjmuytrfcbvjhlhfdjsg";
        final String key = "";

        assertThat(vigenere.cypher(plaintText, key)).isEqualTo(plaintText);
    }

    @Test
    public void shouldNotFailWithEmptyTextWhenUncyphering() {
        final String plaintText = "";
        final String key = "lemon";

        assertThat(vigenere.uncypher(plaintText, key)).isEqualTo(plaintText);
    }

    @Test
    public void shouldNotFailWithEmptyKeyWhenUncyphering() {
        final String plaintText = "djhdjhaoihdugqdjqsdhqsbdsquytdfgcvbjiuytresdfcgvhbjiuytredfgcvbjkiuytrdgfvnbjmuytrfcbvjhlhfdjsg";
        final String key = "";

        assertThat(vigenere.uncypher(plaintText, key)).isEqualTo(plaintText);
    }

}
