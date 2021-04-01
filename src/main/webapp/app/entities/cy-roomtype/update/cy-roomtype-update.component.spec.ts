jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CyRoomtypeService } from '../service/cy-roomtype.service';
import { ICyRoomtype, CyRoomtype } from '../cy-roomtype.model';

import { CyRoomtypeUpdateComponent } from './cy-roomtype-update.component';

describe('Component Tests', () => {
  describe('CyRoomtype Management Update Component', () => {
    let comp: CyRoomtypeUpdateComponent;
    let fixture: ComponentFixture<CyRoomtypeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let cyRoomtypeService: CyRoomtypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CyRoomtypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CyRoomtypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CyRoomtypeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      cyRoomtypeService = TestBed.inject(CyRoomtypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const cyRoomtype: ICyRoomtype = { id: 456 };

        activatedRoute.data = of({ cyRoomtype });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(cyRoomtype));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cyRoomtype = { id: 123 };
        spyOn(cyRoomtypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cyRoomtype });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cyRoomtype }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(cyRoomtypeService.update).toHaveBeenCalledWith(cyRoomtype);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cyRoomtype = new CyRoomtype();
        spyOn(cyRoomtypeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cyRoomtype });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cyRoomtype }));
        saveSubject.complete();

        // THEN
        expect(cyRoomtypeService.create).toHaveBeenCalledWith(cyRoomtype);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cyRoomtype = { id: 123 };
        spyOn(cyRoomtypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cyRoomtype });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(cyRoomtypeService.update).toHaveBeenCalledWith(cyRoomtype);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
