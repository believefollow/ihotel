jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DCktimeService } from '../service/d-cktime.service';
import { IDCktime, DCktime } from '../d-cktime.model';

import { DCktimeUpdateComponent } from './d-cktime-update.component';

describe('Component Tests', () => {
  describe('DCktime Management Update Component', () => {
    let comp: DCktimeUpdateComponent;
    let fixture: ComponentFixture<DCktimeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dCktimeService: DCktimeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DCktimeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DCktimeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DCktimeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dCktimeService = TestBed.inject(DCktimeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dCktime: IDCktime = { id: 456 };

        activatedRoute.data = of({ dCktime });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dCktime));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dCktime = { id: 123 };
        spyOn(dCktimeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dCktime });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dCktime }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dCktimeService.update).toHaveBeenCalledWith(dCktime);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dCktime = new DCktime();
        spyOn(dCktimeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dCktime });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dCktime }));
        saveSubject.complete();

        // THEN
        expect(dCktimeService.create).toHaveBeenCalledWith(dCktime);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dCktime = { id: 123 };
        spyOn(dCktimeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dCktime });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dCktimeService.update).toHaveBeenCalledWith(dCktime);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
