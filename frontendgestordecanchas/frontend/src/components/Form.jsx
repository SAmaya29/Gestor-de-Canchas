import React, { useState } from 'react';
import Button from './Button';

export default function Form({children, ...props}) {
    return (
        <form {...props}>
            {children}
        </form>
    );
};