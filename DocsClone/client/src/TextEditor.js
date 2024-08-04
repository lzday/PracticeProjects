import { useCallback} from 'react'
import Quill from "quill";
import "quill/dist/quill.snow.css"
import "./styles.css"

export default function TextEditor() {
    const wrapperRef = useCallback((wrapper) => { // creates a text editor inside a container using quill
        if(wrapper==null) return // make sure we have a wrapper

        wrapper.innerHTML = "" // cleans the page
        const editor = document.createElement('div')
        wrapper.append(editor) // adds the tool bar to the editor 
        new Quill(editor, {theme: "snow"})
    }, [])
    return <div className="container" ref={wrapperRef}></div>
}
