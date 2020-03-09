/**
 * Created by pll on 04.05.2017.
 */
function intervjuerListeEndret() {
    var liste = document.getElementById("intervjuer")
    var listeValg = liste.options[liste.selectedIndex].value
    if (listeValg) {
        document.getElementById("initialer").value = ''
    }
}

function intervjuerInitialerEndret() {
    var initialer = document.getElementById("initialer").value
    if (initialer) {
        var liste = document.getElementById("intervjuer").selectedIndex = 0;
    }
}
