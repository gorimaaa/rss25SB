import React, { useState } from "react";

function App() {
    const [file, setFile] = useState(null);
    const [url, setUrl] = useState("http://localhost:8080/rss25SB/insert");
    const [response, setResponse] = useState("");

    // STYLES
    const bg = "#191c23";
    const card = {
        maxWidth: 430,
        margin: "70px auto",
        padding: "36px 32px 28px 32px",
        background: "#232633",
        borderRadius: 16,
        boxShadow: "0 8px 32px 0 #14161e44",
        border: "1.5px solid #313447"
    };
    const titre = {
        color: "#fff",
        fontSize: "2rem",
        marginBottom: 24,
        textAlign: "center",
        fontWeight: 700,
        letterSpacing: 1
    };
    const label = {
        color: "#b1b4be",
        display: "block",
        marginBottom: 7,
        fontWeight: 500,
        fontSize: "1rem"
    };
    const inputText = {
        width: "100%",
        borderRadius: 8,
        border: "1.5px solid #363953",
        padding: 10,
        background: "#292c3a",
        color: "#f3f6fa",
        fontSize: "1rem",
        outline: "none",
        transition: "border-color 0.2s",
        marginBottom: 7
    };
    const button = {
        marginTop: 16,
        width: "100%",
        padding: "12px 0",
        background: "linear-gradient(90deg, #4686e0, #53a7fd)",
        color: "#fff",
        fontSize: "1.1rem",
        border: "none",
        borderRadius: 8,
        cursor: "pointer",
        fontWeight: 600,
        boxShadow: "0 4px 16px 0 #2629391a",
        transition: "background 0.2s, transform 0.1s"
    };
    const responseBlock = {
        background: "#1e202a",
        borderRadius: 10,
        marginTop: 26,
        padding: "16px 10px",
        color: "#ececec",
        fontSize: "1rem",
        fontFamily: "JetBrains Mono, Fira Mono, monospace",
        overflowX: "auto",
        boxShadow: "0 4px 18px 0 #22243612",
        wordBreak: "break-all"
    };
    const block = { marginBottom: 18 };

    // LOGIQUE
    const handleFileChange = (e) => setFile(e.target.files[0]);
    const handleUrlChange = (e) => setUrl(e.target.value);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!file) {
            setResponse("Sélectionne un fichier XML !");
            return;
        }
        const reader = new FileReader();
        reader.onload = async (event) => {
            const xmlContent = event.target.result;
            try {
                const res = await fetch(url, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/xml",
                        "Accept": "application/xml",
                    },
                    body: xmlContent,
                });
                const text = await res.text();
                setResponse(`Code ${res.status} : ${text}`);
            } catch (error) {
                setResponse(`Erreur lors de l’envoi : ${error.message}`);
            }
        };
        reader.readAsText(file);
    };

    // FOND sombre sur body (astuce)
    document.body.style.background = bg;

    return (
        <div style={{
            minHeight: "100vh",
            width: "100vw",
            background: bg,
            display: "flex",
            alignItems: "center",
            justifyContent: "center"
        }}>
            <div style={card}>
                <h2 style={titre}>Outil de Transfert<br />RSS25SB</h2>
                <form onSubmit={handleSubmit}>
                    <div style={block}>
                        <label style={label}>
                            Fichier XML :
                            <input type="file" accept=".xml" onChange={handleFileChange}
                                   style={{
                                       marginTop: 5,
                                       color: "#eaeaea",
                                       background: "none",
                                       fontSize: "1em",
                                       cursor: "pointer"
                                   }}
                            />
                        </label>
                    </div>
                    <div style={block}>
                        <label style={label}>
                            URL du service REST :
                            <input
                                type="text"
                                value={url}
                                onChange={handleUrlChange}
                                style={inputText}
                            />
                        </label>
                    </div>
                    <button type="submit" style={button}>Envoyer</button>
                </form>
                {response && (
                    <div style={responseBlock}>
                        <strong style={{ color: "#53a7fd" }}>Réponse du serveur :</strong>
                        <pre style={{ margin: 0 }}>{response}</pre>
                    </div>
                )}
            </div>
        </div>
    );

}

export default App;
