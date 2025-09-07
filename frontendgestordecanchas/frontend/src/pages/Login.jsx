import Form from "../components/Form"
import { useState } from "react"    
export default function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    return (
        <div>
            <h2>Bienvenido Otra Vez</h2>
            <h1>Inicia sesión en tu cuenta</h1>
            <Form>
                <input type="email" placeholder="Correo electrónico" />
                <input type="password" placeholder="Contraseña" />
            </Form>
        </div>
    )
}