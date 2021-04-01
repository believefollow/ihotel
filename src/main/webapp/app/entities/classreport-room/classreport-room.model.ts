export interface IClassreportRoom {
  id?: number;
  account?: string;
  roomn?: string | null;
  yfj?: number | null;
  yfj9008?: number | null;
  yfj9009?: number | null;
  yfj9007?: number | null;
  gz?: number | null;
  ff?: number | null;
  minibar?: number | null;
  phone?: number | null;
  other?: number | null;
  pc?: number | null;
  cz?: number | null;
  cy?: number | null;
  md?: number | null;
  huiy?: number | null;
  dtb?: number | null;
  sszx?: number | null;
}

export class ClassreportRoom implements IClassreportRoom {
  constructor(
    public id?: number,
    public account?: string,
    public roomn?: string | null,
    public yfj?: number | null,
    public yfj9008?: number | null,
    public yfj9009?: number | null,
    public yfj9007?: number | null,
    public gz?: number | null,
    public ff?: number | null,
    public minibar?: number | null,
    public phone?: number | null,
    public other?: number | null,
    public pc?: number | null,
    public cz?: number | null,
    public cy?: number | null,
    public md?: number | null,
    public huiy?: number | null,
    public dtb?: number | null,
    public sszx?: number | null
  ) {}
}

export function getClassreportRoomIdentifier(classreportRoom: IClassreportRoom): number | undefined {
  return classreportRoom.id;
}
