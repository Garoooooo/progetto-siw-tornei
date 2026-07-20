import { useEffect, useState } from 'react';

function App() {
    const [arbitri, setArbitri] = useState([]);
    const [ruolo, setRuolo] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8080/api/arbitri", {
            credentials: "include"
        })
            .then(response => response.json())
            .then(data => setArbitri(data))
            .catch(error => console.log("Errore:", error));
    }, []);

    useEffect(() => {
        fetch("http://localhost:8080/api/utente", {
            credentials: "include"
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                setRuolo(data.ruolo);
            })
            .catch(error => console.log("Errore:", error));
    }, []);

    function eliminaArbitro(id) {
        fetch(`http://localhost:8080/api/arbitri/${id}`, {
            method: "DELETE",
            credentials: "include"
        })
            .then(() => {
                setArbitri(arbitri.filter(a => a.id !== id));
            })
            .catch(error => console.log("Errore:", error));
    }

    return (
        <div>
            <h1>Lista arbitri registrati</h1>

            <table border="1">
                <thead>
                <tr>
                    <th>Nome</th>
                    <th>Cognome</th>
                    <th>Codice arbitrale</th>
                    <th>Dettaglio</th>
                    {ruolo === "ADMIN" && <th>Elimina arbitro</th>}
                </tr>
                </thead>

                <tbody>
                {arbitri.map(arbitro => (
                    <tr key={arbitro.id}>
                        <td>{arbitro.nome}</td>
                        <td>{arbitro.cognome}</td>
                        <td>{arbitro.codiceArbitrale}</td>
                        <td>
                            <a href={`http://localhost:8080/arbitro/${arbitro.id}`}>
                                Vai al dettaglio
                            </a>
                        </td>
                        {ruolo === "ADMIN" && (
                            <td>
                                <button onClick={() => eliminaArbitro(arbitro.id)}>
                                    Rimuovi arbitro
                                </button>
                            </td>
                        )}
                    </tr>
                ))}
                </tbody>
            </table>

            {ruolo === "ADMIN" && (
                <div>
                    <a href="http://localhost:8080/admin/listaArbitri/nuovo">
                        Inserisci nuovo arbitro
                    </a>
                </div>
            )}

            <br />

            <a href="http://localhost:8080/home">
                Torna alla home
            </a>
        </div>
    );
}

export default App;