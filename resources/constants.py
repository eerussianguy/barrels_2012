from typing import Dict, List, NamedTuple, Sequence, Optional, Literal, Tuple, Any

LANGS: Dict[str, str] = {
}

def lang(key: str, *args) -> str:
    return ((key % args) if len(args) > 0 else key).replace('_', ' ').replace('/', ' ').title()