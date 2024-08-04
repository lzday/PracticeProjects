import { useCallback} from 'react'
import Quill from "quill";
import "quill/dist/quill.snow.css"
import "./styles.css"

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
    const wrapperRef = useCallback((wrapper) => { // creates a text editor inside a container using quill
        if(wrapper==null) return // make sure we have a wrapper

        wrapper.innerHTML = "" // cleans the page
        const editor = document.createElement('div')
        wrapper.append(editor) // adds the tool bar to the editor 
        new Quill(editor, {theme: "snow", modules: {toolbar: TOOLBAR_OPTIONS}})
    }, [])
    return <div className="container" ref={wrapperRef}></div>
}
