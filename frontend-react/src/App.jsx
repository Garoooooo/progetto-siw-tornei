import { useEffect, useState } from 'react';

function App() {
  const [arbitri, setArbitri] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/arbitri", {
      credentials: "include"
    })
        .then(response => response.json())
        .then(data => setArbitri(data))
        .catch(error => console.log("Errore:", error));
  }, []);
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
              </tr>
          ))}
          </tbody>
        </table>

        <br />


        <a href="http://localhost:8080/home">
          Torna alla home
        </a>
      </div>
  );
}

export default App;