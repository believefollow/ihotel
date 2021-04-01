export interface ICyRoomtype {
  id?: number;
  rtdm?: string;
  minc?: number | null;
  servicerate?: number | null;
  printer?: string | null;
  printnum?: number | null;
}

export class CyRoomtype implements ICyRoomtype {
  constructor(
    public id?: number,
    public rtdm?: string,
    public minc?: number | null,
    public servicerate?: number | null,
    public printer?: string | null,
    public printnum?: number | null
  ) {}
}

export function getCyRoomtypeIdentifier(cyRoomtype: ICyRoomtype): number | undefined {
  return cyRoomtype.id;
}
