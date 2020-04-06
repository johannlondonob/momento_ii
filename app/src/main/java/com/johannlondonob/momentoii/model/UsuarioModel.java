package com.johannlondonob.momentoii.model;

import java.io.Serializable;

public class UsuarioModel implements Serializable {
    private String _id;
    private String _nombres;
    private String _apellidos;
    private String _correo;
    private String _usuario;
    private String _clave;
    private String _cargo;

    public UsuarioModel(String _id, String _nombres, String _apellidos, String _correo, String _usuario, String _clave, String _cargo) {
        this._id = _id;
        this._nombres = _nombres;
        this._apellidos = _apellidos;
        this._correo = _correo;
        this._usuario = _usuario;
        this._clave = _clave;
        this._cargo = _cargo;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_nombres() {
        return _nombres;
    }

    public void set_nombres(String _nombres) {
        this._nombres = _nombres;
    }

    public String get_apellidos() {
        return _apellidos;
    }

    public void set_apellidos(String _apellidos) {
        this._apellidos = _apellidos;
    }

    public String get_correo() {
        return _correo;
    }

    public void set_correo(String _correo) {
        this._correo = _correo;
    }

    public String get_usuario() {
        return _usuario;
    }

    public void set_usuario(String _usuario) {
        this._usuario = _usuario;
    }

    public String get_clave() {
        return _clave;
    }

    public void set_clave(String _clave) {
        this._clave = _clave;
    }

    public String get_cargo() {
        return _cargo;
    }

    public void set_cargo(String _cargo) {
        this._cargo = _cargo;
    }
}
