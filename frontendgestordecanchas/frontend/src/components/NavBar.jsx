import Button from './Button';
import "../css/NavBar.css"

export default function NavBar() {
    return (
        <nav>
            <div>
                <h1>Logo o logotipo</h1>
            </div>
            <div>
                <Button className="navbar-btn">Reservar</Button>
                <Button className="navbar-btn">Mis Reservas</Button>
            </div>
            <div>
                <Button className="navbar-btn">Cerrar sesión</Button>
                <Button className="navbar-btn">Perfil</Button>
            </div>
        </nav>
    );
}
