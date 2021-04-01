jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DCktypeService } from '../service/d-cktype.service';
import { IDCktype, DCktype } from '../d-cktype.model';

import { DCktypeUpdateComponent } from './d-cktype-update.component';

describe('Component Tests', () => {
  describe('DCktype Management Update Component', () => {
    let comp: DCktypeUpdateComponent;
    let fixture: ComponentFixture<DCktypeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dCktypeService: DCktypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DCktypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DCktypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DCktypeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dCktypeService = TestBed.inject(DCktypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dCktype: IDCktype = { id: 456 };

        activatedRoute.data = of({ dCktype });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dCktype));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dCktype = { id: 123 };
        spyOn(dCktypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dCktype });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dCktype }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dCktypeService.update).toHaveBeenCalledWith(dCktype);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dCktype = new DCktype();
        spyOn(dCktypeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dCktype });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dCktype }));
        saveSubject.complete();

        // THEN
        expect(dCktypeService.create).toHaveBeenCalledWith(dCktype);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dCktype = { id: 123 };
        spyOn(dCktypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dCktype });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dCktypeService.update).toHaveBeenCalledWith(dCktype);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
