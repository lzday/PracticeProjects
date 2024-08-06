import { useCallback, useEffect, useState} from 'react'
import Quill from "quill";
import "quill/dist/quill.snow.css"
import "./styles.css"
import {io} from 'socket.io-client'

const TOOLBAR_OPTIONS = [
    [{header: [1, 2, 3, 4, 5, 6, false]}],
    [{font:[]}],
    [{list: "ordered"}, {list: "bullet"}],
    ["bold", "italic", "underline"],
    [{color: []}, {background: [] }],
    [{script: "sub"}, {script: "super"}],
    [{align: [] }],
    ["image", "blockquote", "code-block"],
    ["clean"],
]

export default function TextEditor() {
    // so that there isn't multiple different variable names
    const [socket, setSocket] = useState()
    const [quill, setQuill] = useState()

    useEffect(() => {
        const s = io('http://localhost:3001')
        setSocket(s) // socket is set to this socket

        return () =>{
            s.disconnect()
        }
    }, [])

    useEffect(()=>{ //receive changes from other users
        if(socket==null || quill==null) return // make sure socket and quill exist

        const handler = (delta) =>{
            quill.updateContents(delta) // update document with changes passed from client
        }
        socket.on('receive-changes', handler) // receive changes through socket
        return () => {
            socket.off('receive-changes', handler)
        }
    }, [socket, quill])

    useEffect(()=>{ //send changes to other users
        if(socket==null || quill==null) return // make sure socket and quill exist

        const handler = (delta, oldDelta, source) =>{
            if(source!=='user') return // only send changes from user
            socket.emit("send-changes", delta) // send changes through socket
        }
        quill.on('text-change', handler) // track changes to the text
        return () => {
            quill.off('text-change', handler)
        }
    }, [socket, quill])

    const wrapperRef = useCallback((wrapper) => { // creates a text editor inside a container using quill
        if(wrapper==null) return // make sure we have a wrapper

        wrapper.innerHTML = "" // cleans the page
        const editor = document.createElement('div')
        wrapper.append(editor) // adds the tool bar to the editor 
        const q = new Quill(editor, {theme: "snow", modules: {toolbar: TOOLBAR_OPTIONS}})
        setQuill(q) // quill is set to this quill
    }, [])
    return <div className="container" ref={wrapperRef}></div>
}
