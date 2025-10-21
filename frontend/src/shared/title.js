export function displayTitle(t) {
  return t?.note ?? t?.title ?? t?.description ?? "(no title)";
}
