import React, { useState } from 'react';
import Button from './Button';

export default function Form({children}) {
    return (
        <form className="formulario">
            {children}
            <Button className="btn-submit" type="submit">Enviar</Button>
        </form>
    );
};